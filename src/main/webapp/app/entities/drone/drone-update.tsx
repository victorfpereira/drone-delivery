import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDrone } from 'app/shared/model/drone.model';
import { StatusDrone } from 'app/shared/model/enumerations/status-drone.model';
import { getEntity, updateEntity, createEntity, reset } from './drone.reducer';

export const DroneUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const droneEntity = useAppSelector(state => state.drone.entity);
  const loading = useAppSelector(state => state.drone.loading);
  const updating = useAppSelector(state => state.drone.updating);
  const updateSuccess = useAppSelector(state => state.drone.updateSuccess);
  const statusDroneValues = Object.keys(StatusDrone);

  const handleClose = () => {
    navigate('/drone' + location.search);
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
      ...droneEntity,
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
          statusDrone: 'LIBERADO',
          ...droneEntity,
          criadoEm: convertDateTimeFromServer(droneEntity.criadoEm),
          atualizadoEm: convertDateTimeFromServer(droneEntity.atualizadoEm),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dronedeliveryApp.drone.home.createOrEditLabel" data-cy="DroneCreateUpdateHeading">
            <Translate contentKey="dronedeliveryApp.drone.home.createOrEditLabel">Create or edit a Drone</Translate>
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
                  id="drone-id"
                  label={translate('dronedeliveryApp.drone.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dronedeliveryApp.drone.codigo')}
                id="drone-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.drone.nome')}
                id="drone-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.drone.descricao')}
                id="drone-descricao"
                name="descricao"
                data-cy="descricao"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.drone.statusDrone')}
                id="drone-statusDrone"
                name="statusDrone"
                data-cy="statusDrone"
                type="select"
              >
                {statusDroneValues.map(statusDrone => (
                  <option value={statusDrone} key={statusDrone}>
                    {translate('dronedeliveryApp.StatusDrone.' + statusDrone)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('dronedeliveryApp.drone.criadoEm')}
                id="drone-criadoEm"
                name="criadoEm"
                data-cy="criadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.drone.atualizadoEm')}
                id="drone-atualizadoEm"
                name="atualizadoEm"
                data-cy="atualizadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/drone" replace color="info">
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

export default DroneUpdate;
