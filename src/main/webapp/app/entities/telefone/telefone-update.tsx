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
import { ITelefone } from 'app/shared/model/telefone.model';
import { TipoTelefone } from 'app/shared/model/enumerations/tipo-telefone.model';
import { getEntity, updateEntity, createEntity, reset } from './telefone.reducer';

export const TelefoneUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const empresas = useAppSelector(state => state.empresa.entities);
  const clientes = useAppSelector(state => state.cliente.entities);
  const telefoneEntity = useAppSelector(state => state.telefone.entity);
  const loading = useAppSelector(state => state.telefone.loading);
  const updating = useAppSelector(state => state.telefone.updating);
  const updateSuccess = useAppSelector(state => state.telefone.updateSuccess);
  const tipoTelefoneValues = Object.keys(TipoTelefone);

  const handleClose = () => {
    navigate('/telefone' + location.search);
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
      ...telefoneEntity,
      ...values,
      empresa: empresas.find(it => it.id.toString() === values.empresa.toString()),
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
          tipoTelefone: 'FIXO',
          ...telefoneEntity,
          criadoEm: convertDateTimeFromServer(telefoneEntity.criadoEm),
          atualizadoEm: convertDateTimeFromServer(telefoneEntity.atualizadoEm),
          empresa: telefoneEntity?.empresa?.id,
          cliente: telefoneEntity?.cliente?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dronedeliveryApp.telefone.home.createOrEditLabel" data-cy="TelefoneCreateUpdateHeading">
            <Translate contentKey="dronedeliveryApp.telefone.home.createOrEditLabel">Create or edit a Telefone</Translate>
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
                  id="telefone-id"
                  label={translate('dronedeliveryApp.telefone.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dronedeliveryApp.telefone.ddd')}
                id="telefone-ddd"
                name="ddd"
                data-cy="ddd"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.telefone.numero')}
                id="telefone-numero"
                name="numero"
                data-cy="numero"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.telefone.tipoTelefone')}
                id="telefone-tipoTelefone"
                name="tipoTelefone"
                data-cy="tipoTelefone"
                type="select"
              >
                {tipoTelefoneValues.map(tipoTelefone => (
                  <option value={tipoTelefone} key={tipoTelefone}>
                    {translate('dronedeliveryApp.TipoTelefone.' + tipoTelefone)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('dronedeliveryApp.telefone.status')}
                id="telefone-status"
                name="status"
                data-cy="status"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.telefone.criadoEm')}
                id="telefone-criadoEm"
                name="criadoEm"
                data-cy="criadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.telefone.atualizadoEm')}
                id="telefone-atualizadoEm"
                name="atualizadoEm"
                data-cy="atualizadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="telefone-empresa"
                name="empresa"
                data-cy="empresa"
                label={translate('dronedeliveryApp.telefone.empresa')}
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
                id="telefone-cliente"
                name="cliente"
                data-cy="cliente"
                label={translate('dronedeliveryApp.telefone.cliente')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/telefone" replace color="info">
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

export default TelefoneUpdate;
