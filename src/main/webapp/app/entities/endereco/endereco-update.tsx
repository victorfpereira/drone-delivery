import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { getEntities as getEmpresas } from 'app/entities/empresa/empresa.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { IEndereco } from 'app/shared/model/endereco.model';
import { TipoEndereco } from 'app/shared/model/enumerations/tipo-endereco.model';
import { getEntity, updateEntity, createEntity, reset } from './endereco.reducer';

export const EnderecoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const empresas = useAppSelector(state => state.empresa.entities);
  const clientes = useAppSelector(state => state.cliente.entities);
  const enderecoEntity = useAppSelector(state => state.endereco.entity);
  const loading = useAppSelector(state => state.endereco.loading);
  const updating = useAppSelector(state => state.endereco.updating);
  const updateSuccess = useAppSelector(state => state.endereco.updateSuccess);
  const tipoEnderecoValues = Object.keys(TipoEndereco);

  const handleClose = () => {
    navigate('/endereco' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmpresas({}));
    dispatch(getClientes({}));
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
      ...enderecoEntity,
      ...values,
      razaoSocial: empresas.find(it => it.id.toString() === values.razaoSocial.toString()),
      cliente: clientes.find(it => it.id.toString() === values.cliente.toString()),
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
          tipoEndereco: 'RESIDENCIAL',
          ...enderecoEntity,
          criadoEm: convertDateTimeFromServer(enderecoEntity.criadoEm),
          atualizadoEm: convertDateTimeFromServer(enderecoEntity.atualizadoEm),
          razaoSocial: enderecoEntity?.razaoSocial?.id,
          cliente: enderecoEntity?.cliente?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dronedeliveryApp.endereco.home.createOrEditLabel" data-cy="EnderecoCreateUpdateHeading">
            <Translate contentKey="dronedeliveryApp.endereco.home.createOrEditLabel">Create or edit a Endereco</Translate>
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
                  id="endereco-id"
                  label={translate('dronedeliveryApp.endereco.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('dronedeliveryApp.endereco.rua')} id="endereco-rua" name="rua" data-cy="rua" type="text" />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.numero')}
                id="endereco-numero"
                name="numero"
                data-cy="numero"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.bairro')}
                id="endereco-bairro"
                name="bairro"
                data-cy="bairro"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.cidade')}
                id="endereco-cidade"
                name="cidade"
                data-cy="cidade"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.estado')}
                id="endereco-estado"
                name="estado"
                data-cy="estado"
                type="text"
              />
              <ValidatedField label={translate('dronedeliveryApp.endereco.cep')} id="endereco-cep" name="cep" data-cy="cep" type="text" />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.complemento')}
                id="endereco-complemento"
                name="complemento"
                data-cy="complemento"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.referencia')}
                id="endereco-referencia"
                name="referencia"
                data-cy="referencia"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.enderecoCompleto')}
                id="endereco-enderecoCompleto"
                name="enderecoCompleto"
                data-cy="enderecoCompleto"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.tipoEndereco')}
                id="endereco-tipoEndereco"
                name="tipoEndereco"
                data-cy="tipoEndereco"
                type="select"
              >
                {tipoEnderecoValues.map(tipoEndereco => (
                  <option value={tipoEndereco} key={tipoEndereco}>
                    {translate('dronedeliveryApp.TipoEndereco.' + tipoEndereco)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.latitude')}
                id="endereco-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.longitude')}
                id="endereco-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.status')}
                id="endereco-status"
                name="status"
                data-cy="status"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.criadoEm')}
                id="endereco-criadoEm"
                name="criadoEm"
                data-cy="criadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.endereco.atualizadoEm')}
                id="endereco-atualizadoEm"
                name="atualizadoEm"
                data-cy="atualizadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="endereco-razaoSocial"
                name="razaoSocial"
                data-cy="razaoSocial"
                label={translate('dronedeliveryApp.endereco.razaoSocial')}
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
                id="endereco-cliente"
                name="cliente"
                data-cy="cliente"
                label={translate('dronedeliveryApp.endereco.cliente')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/endereco" replace color="info">
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

export default EnderecoUpdate;
