import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { getEntities as getEmpresas } from 'app/entities/empresa/empresa.reducer';
import { IEndereco } from 'app/shared/model/endereco.model';
import { getEntities as getEnderecos } from 'app/entities/endereco/endereco.reducer';
import { IPedido } from 'app/shared/model/pedido.model';
import { StatusPedido } from 'app/shared/model/enumerations/status-pedido.model';
import { getEntity, updateEntity, createEntity, reset } from './pedido.reducer';

export const PedidoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientes = useAppSelector(state => state.cliente.entities);
  const empresas = useAppSelector(state => state.empresa.entities);
  const enderecos = useAppSelector(state => state.endereco.entities);
  const pedidoEntity = useAppSelector(state => state.pedido.entity);
  const loading = useAppSelector(state => state.pedido.loading);
  const updating = useAppSelector(state => state.pedido.updating);
  const updateSuccess = useAppSelector(state => state.pedido.updateSuccess);
  const statusPedidoValues = Object.keys(StatusPedido);

  const handleClose = () => {
    navigate('/pedido' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClientes({}));
    dispatch(getEmpresas({}));
    dispatch(getEnderecos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.criadoEm = convertDateTimeToServer(values.criadoEm);
    values.atualizadoEm = convertDateTimeToServer(values.atualizadoEm);

    const entity = {
      ...pedidoEntity,
      ...values,
      solicitante: clientes.find(it => it.id.toString() === values.solicitante.toString()),
      solicitado: empresas.find(it => it.id.toString() === values.solicitado.toString()),
      endereco: enderecos.find(it => it.id.toString() === values.endereco.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          criadoEm: displayDefaultDateTime(),
          atualizadoEm: displayDefaultDateTime(),
        }
      : {
          statusPedido: 'ABERTO',
          ...pedidoEntity,
          criadoEm: convertDateTimeFromServer(pedidoEntity.criadoEm),
          atualizadoEm: convertDateTimeFromServer(pedidoEntity.atualizadoEm),
          solicitante: pedidoEntity?.solicitante?.id,
          solicitado: pedidoEntity?.solicitado?.id,
          endereco: pedidoEntity?.endereco?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dronedeliveryApp.pedido.home.createOrEditLabel" data-cy="PedidoCreateUpdateHeading">
            <Translate contentKey="dronedeliveryApp.pedido.home.createOrEditLabel">Create or edit a Pedido</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="pedido-id"
                  label={translate('dronedeliveryApp.pedido.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dronedeliveryApp.pedido.codigo')}
                id="pedido-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.pedido.notaFiscal')}
                id="pedido-notaFiscal"
                name="notaFiscal"
                data-cy="notaFiscal"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.pedido.statusPedido')}
                id="pedido-statusPedido"
                name="statusPedido"
                data-cy="statusPedido"
                type="select"
              >
                {statusPedidoValues.map(statusPedido => (
                  <option value={statusPedido} key={statusPedido}>
                    {translate('dronedeliveryApp.StatusPedido.' + statusPedido)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('dronedeliveryApp.pedido.criadoEm')}
                id="pedido-criadoEm"
                name="criadoEm"
                data-cy="criadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.pedido.atualizadoEm')}
                id="pedido-atualizadoEm"
                name="atualizadoEm"
                data-cy="atualizadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="pedido-solicitante"
                name="solicitante"
                data-cy="solicitante"
                label={translate('dronedeliveryApp.pedido.solicitante')}
                type="select"
              >
                <option value="" key="0" />
                {clientes
                  ? clientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="pedido-solicitado"
                name="solicitado"
                data-cy="solicitado"
                label={translate('dronedeliveryApp.pedido.solicitado')}
                type="select"
              >
                <option value="" key="0" />
                {empresas
                  ? empresas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="pedido-endereco"
                name="endereco"
                data-cy="endereco"
                label={translate('dronedeliveryApp.pedido.endereco')}
                type="select"
              >
                <option value="" key="0" />
                {enderecos
                  ? enderecos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pedido" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PedidoUpdate;
