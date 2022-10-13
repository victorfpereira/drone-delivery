import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './agendamento.reducer';

export const AgendamentoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const agendamentoEntity = useAppSelector(state => state.agendamento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="agendamentoDetailsHeading">
          <Translate contentKey="dronedeliveryApp.agendamento.detail.title">Agendamento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="dronedeliveryApp.agendamento.id">Id</Translate>
            </span>
          </dt>
          <dd>{agendamentoEntity.id}</dd>
          <dt>
            <span id="dataAgendada">
              <Translate contentKey="dronedeliveryApp.agendamento.dataAgendada">Data Agendada</Translate>
            </span>
          </dt>
          <dd>
            {agendamentoEntity.dataAgendada ? (
              <TextFormat value={agendamentoEntity.dataAgendada} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="statusAgendamento">
              <Translate contentKey="dronedeliveryApp.agendamento.statusAgendamento">Status Agendamento</Translate>
            </span>
          </dt>
          <dd>{agendamentoEntity.statusAgendamento}</dd>
          <dt>
            <span id="criadoEm">
              <Translate contentKey="dronedeliveryApp.agendamento.criadoEm">Criado Em</Translate>
            </span>
          </dt>
          <dd>
            {agendamentoEntity.criadoEm ? <TextFormat value={agendamentoEntity.criadoEm} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="atualizadoEm">
              <Translate contentKey="dronedeliveryApp.agendamento.atualizadoEm">Atualizado Em</Translate>
            </span>
          </dt>
          <dd>
            {agendamentoEntity.atualizadoEm ? (
              <TextFormat value={agendamentoEntity.atualizadoEm} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.agendamento.drone">Drone</Translate>
          </dt>
          <dd>{agendamentoEntity.drone ? agendamentoEntity.drone.id : ''}</dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.agendamento.pedido">Pedido</Translate>
          </dt>
          <dd>{agendamentoEntity.pedido ? agendamentoEntity.pedido.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/agendamento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/agendamento/${agendamentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgendamentoDetail;
