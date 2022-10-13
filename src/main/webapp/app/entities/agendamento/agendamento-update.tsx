import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDrone } from 'app/shared/model/drone.model';
import { getEntities as getDrones } from 'app/entities/drone/drone.reducer';
import { IPedido } from 'app/shared/model/pedido.model';
import { getEntities as getPedidos } from 'app/entities/pedido/pedido.reducer';
import { IAgendamento } from 'app/shared/model/agendamento.model';
import { StatusAgendamento } from 'app/shared/model/enumerations/status-agendamento.model';
import { getEntity, updateEntity, createEntity, reset } from './agendamento.reducer';

export const AgendamentoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const drones = useAppSelector(state => state.drone.entities);
  const pedidos = useAppSelector(state => state.pedido.entities);
  const agendamentoEntity = useAppSelector(state => state.agendamento.entity);
  const loading = useAppSelector(state => state.agendamento.loading);
  const updating = useAppSelector(state => state.agendamento.updating);
  const updateSuccess = useAppSelector(state => state.agendamento.updateSuccess);
  const statusAgendamentoValues = Object.keys(StatusAgendamento);

  const handleClose = () => {
    navigate('/agendamento' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDrones({}));
    dispatch(getPedidos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dataAgendada = convertDateTimeToServer(values.dataAgendada);
    values.criadoEm = convertDateTimeToServer(values.criadoEm);
    values.atualizadoEm = convertDateTimeToServer(values.atualizadoEm);

    const entity = {
      ...agendamentoEntity,
      ...values,
      drone: drones.find(it => it.id.toString() === values.drone.toString()),
      pedido: pedidos.find(it => it.id.toString() === values.pedido.toString()),
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
          dataAgendada: displayDefaultDateTime(),
          criadoEm: displayDefaultDateTime(),
          atualizadoEm: displayDefaultDateTime(),
        }
      : {
          statusAgendamento: 'AGENDADO',
          ...agendamentoEntity,
          dataAgendada: convertDateTimeFromServer(agendamentoEntity.dataAgendada),
          criadoEm: convertDateTimeFromServer(agendamentoEntity.criadoEm),
          atualizadoEm: convertDateTimeFromServer(agendamentoEntity.atualizadoEm),
          drone: agendamentoEntity?.drone?.id,
          pedido: agendamentoEntity?.pedido?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dronedeliveryApp.agendamento.home.createOrEditLabel" data-cy="AgendamentoCreateUpdateHeading">
            <Translate contentKey="dronedeliveryApp.agendamento.home.createOrEditLabel">Create or edit a Agendamento</Translate>
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
                  id="agendamento-id"
                  label={translate('dronedeliveryApp.agendamento.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dronedeliveryApp.agendamento.dataAgendada')}
                id="agendamento-dataAgendada"
                name="dataAgendada"
                data-cy="dataAgendada"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.agendamento.statusAgendamento')}
                id="agendamento-statusAgendamento"
                name="statusAgendamento"
                data-cy="statusAgendamento"
                type="select"
              >
                {statusAgendamentoValues.map(statusAgendamento => (
                  <option value={statusAgendamento} key={statusAgendamento}>
                    {translate('dronedeliveryApp.StatusAgendamento.' + statusAgendamento)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('dronedeliveryApp.agendamento.criadoEm')}
                id="agendamento-criadoEm"
                name="criadoEm"
                data-cy="criadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.agendamento.atualizadoEm')}
                id="agendamento-atualizadoEm"
                name="atualizadoEm"
                data-cy="atualizadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="agendamento-drone"
                name="drone"
                data-cy="drone"
                label={translate('dronedeliveryApp.agendamento.drone')}
                type="select"
              >
                <option value="" key="0" />
                {drones
                  ? drones.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="agendamento-pedido"
                name="pedido"
                data-cy="pedido"
                label={translate('dronedeliveryApp.agendamento.pedido')}
                type="select"
              >
                <option value="" key="0" />
                {pedidos
                  ? pedidos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/agendamento" replace color="info">
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

export default AgendamentoUpdate;
