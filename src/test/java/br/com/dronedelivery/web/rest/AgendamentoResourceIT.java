package br.com.dronedelivery.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.dronedelivery.IntegrationTest;
import br.com.dronedelivery.domain.Agendamento;
import br.com.dronedelivery.domain.enumeration.StatusAgendamento;
import br.com.dronedelivery.repository.AgendamentoRepository;
import br.com.dronedelivery.service.dto.AgendamentoDTO;
import br.com.dronedelivery.service.mapper.AgendamentoMapper;
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
 * Integration tests for the {@link AgendamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgendamentoResourceIT {

    private static final Instant DEFAULT_DATA_AGENDADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_AGENDADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusAgendamento DEFAULT_STATUS_AGENDAMENTO = StatusAgendamento.AGENDADO;
    private static final StatusAgendamento UPDATED_STATUS_AGENDAMENTO = StatusAgendamento.CONCLUIDO;

    private static final Instant DEFAULT_CRIADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRIADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ATUALIZADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATUALIZADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/agendamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private AgendamentoMapper agendamentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendamentoMockMvc;

    private Agendamento agendamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendamento createEntity(EntityManager em) {
        Agendamento agendamento = new Agendamento()
            .dataAgendada(DEFAULT_DATA_AGENDADA)
            .statusAgendamento(DEFAULT_STATUS_AGENDAMENTO)
            .criadoEm(DEFAULT_CRIADO_EM)
            .atualizadoEm(DEFAULT_ATUALIZADO_EM);
        return agendamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendamento createUpdatedEntity(EntityManager em) {
        Agendamento agendamento = new Agendamento()
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .statusAgendamento(UPDATED_STATUS_AGENDAMENTO)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
        return agendamento;
    }

    @BeforeEach
    public void initTest() {
        agendamento = createEntity(em);
    }

    @Test
    @Transactional
    void createAgendamento() throws Exception {
        int databaseSizeBeforeCreate = agendamentoRepository.findAll().size();
        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);
        restAgendamentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Agendamento testAgendamento = agendamentoList.get(agendamentoList.size() - 1);
        assertThat(testAgendamento.getDataAgendada()).isEqualTo(DEFAULT_DATA_AGENDADA);
        assertThat(testAgendamento.getStatusAgendamento()).isEqualTo(DEFAULT_STATUS_AGENDAMENTO);
        assertThat(testAgendamento.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testAgendamento.getAtualizadoEm()).isEqualTo(DEFAULT_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void createAgendamentoWithExistingId() throws Exception {
        // Create the Agendamento with an existing ID
        agendamentoRepository.saveAndFlush(agendamento);
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        int databaseSizeBeforeCreate = agendamentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendamentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgendamentos() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        // Get all the agendamentoList
        restAgendamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendamento.getId().toString())))
            .andExpect(jsonPath("$.[*].dataAgendada").value(hasItem(DEFAULT_DATA_AGENDADA.toString())))
            .andExpect(jsonPath("$.[*].statusAgendamento").value(hasItem(DEFAULT_STATUS_AGENDAMENTO.toString())))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].atualizadoEm").value(hasItem(DEFAULT_ATUALIZADO_EM.toString())));
    }

    @Test
    @Transactional
    void getAgendamento() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        // Get the agendamento
        restAgendamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, agendamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendamento.getId().toString()))
            .andExpect(jsonPath("$.dataAgendada").value(DEFAULT_DATA_AGENDADA.toString()))
            .andExpect(jsonPath("$.statusAgendamento").value(DEFAULT_STATUS_AGENDAMENTO.toString()))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.atualizadoEm").value(DEFAULT_ATUALIZADO_EM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAgendamento() throws Exception {
        // Get the agendamento
        restAgendamentoMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgendamento() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();

        // Update the agendamento
        Agendamento updatedAgendamento = agendamentoRepository.findById(agendamento.getId()).get();
        // Disconnect from session so that the updates on updatedAgendamento are not directly saved in db
        em.detach(updatedAgendamento);
        updatedAgendamento
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .statusAgendamento(UPDATED_STATUS_AGENDAMENTO)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(updatedAgendamento);

        restAgendamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
        Agendamento testAgendamento = agendamentoList.get(agendamentoList.size() - 1);
        assertThat(testAgendamento.getDataAgendada()).isEqualTo(UPDATED_DATA_AGENDADA);
        assertThat(testAgendamento.getStatusAgendamento()).isEqualTo(UPDATED_STATUS_AGENDAMENTO);
        assertThat(testAgendamento.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testAgendamento.getAtualizadoEm()).isEqualTo(UPDATED_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void putNonExistingAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();
        agendamento.setId(UUID.randomUUID());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();
        agendamento.setId(UUID.randomUUID());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();
        agendamento.setId(UUID.randomUUID());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agendamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendamentoWithPatch() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();

        // Update the agendamento using partial update
        Agendamento partialUpdatedAgendamento = new Agendamento();
        partialUpdatedAgendamento.setId(agendamento.getId());

        partialUpdatedAgendamento.dataAgendada(UPDATED_DATA_AGENDADA).criadoEm(UPDATED_CRIADO_EM);

        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgendamento))
            )
            .andExpect(status().isOk());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
        Agendamento testAgendamento = agendamentoList.get(agendamentoList.size() - 1);
        assertThat(testAgendamento.getDataAgendada()).isEqualTo(UPDATED_DATA_AGENDADA);
        assertThat(testAgendamento.getStatusAgendamento()).isEqualTo(DEFAULT_STATUS_AGENDAMENTO);
        assertThat(testAgendamento.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testAgendamento.getAtualizadoEm()).isEqualTo(DEFAULT_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void fullUpdateAgendamentoWithPatch() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();

        // Update the agendamento using partial update
        Agendamento partialUpdatedAgendamento = new Agendamento();
        partialUpdatedAgendamento.setId(agendamento.getId());

        partialUpdatedAgendamento
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .statusAgendamento(UPDATED_STATUS_AGENDAMENTO)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);

        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgendamento))
            )
            .andExpect(status().isOk());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
        Agendamento testAgendamento = agendamentoList.get(agendamentoList.size() - 1);
        assertThat(testAgendamento.getDataAgendada()).isEqualTo(UPDATED_DATA_AGENDADA);
        assertThat(testAgendamento.getStatusAgendamento()).isEqualTo(UPDATED_STATUS_AGENDAMENTO);
        assertThat(testAgendamento.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testAgendamento.getAtualizadoEm()).isEqualTo(UPDATED_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void patchNonExistingAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();
        agendamento.setId(UUID.randomUUID());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();
        agendamento.setId(UUID.randomUUID());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();
        agendamento.setId(UUID.randomUUID());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(agendamentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendamento() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        int databaseSizeBeforeDelete = agendamentoRepository.findAll().size();

        // Delete the agendamento
        restAgendamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendamento.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
