package br.com.dronedelivery.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.dronedelivery.IntegrationTest;
import br.com.dronedelivery.domain.Pedido;
import br.com.dronedelivery.domain.enumeration.StatusPedido;
import br.com.dronedelivery.repository.PedidoRepository;
import br.com.dronedelivery.service.dto.PedidoDTO;
import br.com.dronedelivery.service.mapper.PedidoMapper;
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
 * Integration tests for the {@link PedidoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PedidoResourceIT {

    private static final Long DEFAULT_CODIGO = 1L;
    private static final Long UPDATED_CODIGO = 2L;

    private static final String DEFAULT_NOTA_FISCAL = "AAAAAAAAAA";
    private static final String UPDATED_NOTA_FISCAL = "BBBBBBBBBB";

    private static final StatusPedido DEFAULT_STATUS_PEDIDO = StatusPedido.ABERTO;
    private static final StatusPedido UPDATED_STATUS_PEDIDO = StatusPedido.TRANSITO_COLETA;

    private static final Instant DEFAULT_CRIADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRIADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ATUALIZADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATUALIZADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/pedidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPedidoMockMvc;

    private Pedido pedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pedido createEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .codigo(DEFAULT_CODIGO)
            .notaFiscal(DEFAULT_NOTA_FISCAL)
            .statusPedido(DEFAULT_STATUS_PEDIDO)
            .criadoEm(DEFAULT_CRIADO_EM)
            .atualizadoEm(DEFAULT_ATUALIZADO_EM);
        return pedido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pedido createUpdatedEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .codigo(UPDATED_CODIGO)
            .notaFiscal(UPDATED_NOTA_FISCAL)
            .statusPedido(UPDATED_STATUS_PEDIDO)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
        return pedido;
    }

    @BeforeEach
    public void initTest() {
        pedido = createEntity(em);
    }

    @Test
    @Transactional
    void createPedido() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();
        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);
        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPedido.getNotaFiscal()).isEqualTo(DEFAULT_NOTA_FISCAL);
        assertThat(testPedido.getStatusPedido()).isEqualTo(DEFAULT_STATUS_PEDIDO);
        assertThat(testPedido.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testPedido.getAtualizadoEm()).isEqualTo(DEFAULT_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void createPedidoWithExistingId() throws Exception {
        // Create the Pedido with an existing ID
        pedidoRepository.saveAndFlush(pedido);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setCodigo(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotaFiscalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setNotaFiscal(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPedidos() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList
        restPedidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].notaFiscal").value(hasItem(DEFAULT_NOTA_FISCAL)))
            .andExpect(jsonPath("$.[*].statusPedido").value(hasItem(DEFAULT_STATUS_PEDIDO.toString())))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].atualizadoEm").value(hasItem(DEFAULT_ATUALIZADO_EM.toString())));
    }

    @Test
    @Transactional
    void getPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get the pedido
        restPedidoMockMvc
            .perform(get(ENTITY_API_URL_ID, pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pedido.getId().toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.intValue()))
            .andExpect(jsonPath("$.notaFiscal").value(DEFAULT_NOTA_FISCAL))
            .andExpect(jsonPath("$.statusPedido").value(DEFAULT_STATUS_PEDIDO.toString()))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.atualizadoEm").value(DEFAULT_ATUALIZADO_EM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPedido() throws Exception {
        // Get the pedido
        restPedidoMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido
        Pedido updatedPedido = pedidoRepository.findById(pedido.getId()).get();
        // Disconnect from session so that the updates on updatedPedido are not directly saved in db
        em.detach(updatedPedido);
        updatedPedido
            .codigo(UPDATED_CODIGO)
            .notaFiscal(UPDATED_NOTA_FISCAL)
            .statusPedido(UPDATED_STATUS_PEDIDO)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(updatedPedido);

        restPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pedidoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPedido.getNotaFiscal()).isEqualTo(UPDATED_NOTA_FISCAL);
        assertThat(testPedido.getStatusPedido()).isEqualTo(UPDATED_STATUS_PEDIDO);
        assertThat(testPedido.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testPedido.getAtualizadoEm()).isEqualTo(UPDATED_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void putNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(UUID.randomUUID());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pedidoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(UUID.randomUUID());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(UUID.randomUUID());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePedidoWithPatch() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido using partial update
        Pedido partialUpdatedPedido = new Pedido();
        partialUpdatedPedido.setId(pedido.getId());

        partialUpdatedPedido.codigo(UPDATED_CODIGO).statusPedido(UPDATED_STATUS_PEDIDO).atualizadoEm(UPDATED_ATUALIZADO_EM);

        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPedido))
            )
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPedido.getNotaFiscal()).isEqualTo(DEFAULT_NOTA_FISCAL);
        assertThat(testPedido.getStatusPedido()).isEqualTo(UPDATED_STATUS_PEDIDO);
        assertThat(testPedido.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testPedido.getAtualizadoEm()).isEqualTo(UPDATED_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void fullUpdatePedidoWithPatch() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido using partial update
        Pedido partialUpdatedPedido = new Pedido();
        partialUpdatedPedido.setId(pedido.getId());

        partialUpdatedPedido
            .codigo(UPDATED_CODIGO)
            .notaFiscal(UPDATED_NOTA_FISCAL)
            .statusPedido(UPDATED_STATUS_PEDIDO)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);

        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPedido))
            )
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPedido.getNotaFiscal()).isEqualTo(UPDATED_NOTA_FISCAL);
        assertThat(testPedido.getStatusPedido()).isEqualTo(UPDATED_STATUS_PEDIDO);
        assertThat(testPedido.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testPedido.getAtualizadoEm()).isEqualTo(UPDATED_ATUALIZADO_EM);
    }

    @Test
    @Transactional
    void patchNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(UUID.randomUUID());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pedidoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(UUID.randomUUID());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(UUID.randomUUID());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeDelete = pedidoRepository.findAll().size();

        // Delete the pedido
        restPedidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pedido.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
