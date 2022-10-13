package br.com.dronedelivery.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.dronedelivery.IntegrationTest;
import br.com.dronedelivery.domain.Telefone;
import br.com.dronedelivery.domain.enumeration.TipoTelefone;
import br.com.dronedelivery.repository.TelefoneRepository;
import br.com.dronedelivery.service.dto.TelefoneDTO;
import br.com.dronedelivery.service.mapper.TelefoneMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TelefoneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelefoneResourceIT {

    private static final String DEFAULT_DDD = "AAAAAAAAAA";
    private static final String UPDATED_DDD = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final TipoTelefone DEFAULT_TIPO_TELEFONE = TipoTelefone.FIXO;
    private static final TipoTelefone UPDATED_TIPO_TELEFONE = TipoTelefone.CELULAR;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Instant DEFAULT_CRIADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRIADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ATUALIZADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATUALIZADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/telefones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoneMockMvc;

    private Telefone telefone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .ddd(DEFAULT_DDD)
            .numero(DEFAULT_NUMERO)
            .tipoTelefone(DEFAULT_TIPO_TELEFONE)
            .status(DEFAULT_STATUS)
            .criadoEm(DEFAULT_CRIADO_EM)
            .atualizadoEm(DEFAULT_ATUALIZADO_EM);
        return telefone;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createUpdatedEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .ddd(UPDATED_DDD)
            .numero(UPDATED_NUMERO)
            .tipoTelefone(UPDATED_TIPO_TELEFONE)
            .status(UPDATED_STATUS)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
        return telefone;
    }

    @BeforeEach
    public void initTest() {
        telefone = createEntity(em);
    }

    @Test
    @Transactional
    void createTelefone() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();
        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);
        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getDdd()).isEqualTo(DEFAULT_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTelefone.getTipoTelefone()).isEqualTo(DEFAULT_TIPO_TELEFONE);
        assertThat(testTelefone.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTelefone.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testTelefone.getAtualizadoEm()).isEqualTo(DEFAULT_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void createTelefoneWithExistingId() throws Exception {
        // Create the Telefone with an existing ID
        telefoneRepository.saveAndFlush(telefone);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDddIsRequired() throws Exception {
        int databaseSizeBeforeTest = telefoneRepository.findAll().size();
        // set the field null
        telefone.setDdd(null);

        // Create the Telefone, which fails.
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = telefoneRepository.findAll().size();
        // set the field null
        telefone.setNumero(null);

        // Create the Telefone, which fails.
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTelefones() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().toString())))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].tipoTelefone").value(hasItem(DEFAULT_TIPO_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].atualizadoEm").value(hasItem(DEFAULT_ATUALIZADO_EM.toString())));
    }

    @Test
    @Transactional
    void getTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get the telefone
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL_ID, telefone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefone.getId().toString()))
            .andExpect(jsonPath("$.ddd").value(DEFAULT_DDD))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.tipoTelefone").value(DEFAULT_TIPO_TELEFONE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.atualizadoEm").value(DEFAULT_ATUALIZADO_EM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTelefone() throws Exception {
        // Get the telefone
        restTelefoneMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone
        Telefone updatedTelefone = telefoneRepository.findById(telefone.getId()).get();
        // Disconnect from session so that the updates on updatedTelefone are not directly saved in db
        em.detach(updatedTelefone);
        updatedTelefone
            .ddd(UPDATED_DDD)
            .numero(UPDATED_NUMERO)
            .tipoTelefone(UPDATED_TIPO_TELEFONE)
            .status(UPDATED_STATUS)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(updatedTelefone);

        restTelefoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telefoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getDdd()).isEqualTo(UPDATED_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getTipoTelefone()).isEqualTo(UPDATED_TIPO_TELEFONE);
        assertThat(testTelefone.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTelefone.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testTelefone.getAtualizadoEm()).isEqualTo(UPDATED_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void putNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telefoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelefoneWithPatch() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone using partial update
        Telefone partialUpdatedTelefone = new Telefone();
        partialUpdatedTelefone.setId(telefone.getId());

        partialUpdatedTelefone.ddd(UPDATED_DDD).numero(UPDATED_NUMERO);

        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelefone))
            )
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getDdd()).isEqualTo(UPDATED_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getTipoTelefone()).isEqualTo(DEFAULT_TIPO_TELEFONE);
        assertThat(testTelefone.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTelefone.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testTelefone.getAtualizadoEm()).isEqualTo(DEFAULT_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void fullUpdateTelefoneWithPatch() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone using partial update
        Telefone partialUpdatedTelefone = new Telefone();
        partialUpdatedTelefone.setId(telefone.getId());

        partialUpdatedTelefone
            .ddd(UPDATED_DDD)
            .numero(UPDATED_NUMERO)
            .tipoTelefone(UPDATED_TIPO_TELEFONE)
            .status(UPDATED_STATUS)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);

        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelefone))
            )
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getDdd()).isEqualTo(UPDATED_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getTipoTelefone()).isEqualTo(UPDATED_TIPO_TELEFONE);
        assertThat(testTelefone.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTelefone.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testTelefone.getAtualizadoEm()).isEqualTo(UPDATED_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void patchNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telefoneDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeDelete = telefoneRepository.findAll().size();

        // Delete the telefone
        restTelefoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, telefone.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
