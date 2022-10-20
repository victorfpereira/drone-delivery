import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICliente } from 'app/shared/model/cliente.model';
import { getEntity, updateEntity, createEntity, reset } from './cliente.reducer';

export const ClienteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clienteEntity = useAppSelector(state => state.cliente.entity);
  const loading = useAppSelector(state => state.cliente.loading);
  const updating = useAppSelector(state => state.cliente.updating);
  const updateSuccess = useAppSelector(state => state.cliente.updateSuccess);

  const handleClose = () => {
    navigate('/cliente' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...clienteEntity,
      ...values,
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
          ...clienteEntity,
          criadoEm: convertDateTimeFromServer(clienteEntity.criadoEm),
          atualizadoEm: convertDateTimeFromServer(clienteEntity.atualizadoEm),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dronedeliveryApp.cliente.home.createOrEditLabel" data-cy="ClienteCreateUpdateHeading">
            <Translate contentKey="dronedeliveryApp.cliente.home.createOrEditLabel">Create or edit a Cliente</Translate>
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
                  id="cliente-id"
                  label={translate('dronedeliveryApp.cliente.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dronedeliveryApp.cliente.nome')}
                id="cliente-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.cliente.documento')}
                id="cliente-documento"
                name="documento"
                data-cy="documento"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 11, message: translate('entity.validation.min', { min: 11 }) },
                  max: { value: 11, message: translate('entity.validation.max', { max: 11 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.cliente.email')}
                id="cliente-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.cliente.status')}
                id="cliente-status"
                name="status"
                data-cy="status"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.cliente.criadoEm')}
                id="cliente-criadoEm"
                name="criadoEm"
                data-cy="criadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.cliente.atualizadoEm')}
                id="cliente-atualizadoEm"
                name="atualizadoEm"
                data-cy="atualizadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cliente" replace color="info">
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

export default ClienteUpdate;
