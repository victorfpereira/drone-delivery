import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './drone.reducer';

export const DroneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const droneEntity = useAppSelector(state => state.drone.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="droneDetailsHeading">
          <Translate contentKey="dronedeliveryApp.drone.detail.title">Drone</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="dronedeliveryApp.drone.id">Id</Translate>
            </span>
          </dt>
          <dd>{droneEntity.id}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="dronedeliveryApp.drone.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{droneEntity.codigo}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="dronedeliveryApp.drone.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{droneEntity.nome}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="dronedeliveryApp.drone.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{droneEntity.descricao}</dd>
          <dt>
            <span id="statusDrone">
              <Translate contentKey="dronedeliveryApp.drone.statusDrone">Status Drone</Translate>
            </span>
          </dt>
          <dd>{droneEntity.statusDrone}</dd>
          <dt>
            <span id="criadoEm">
              <Translate contentKey="dronedeliveryApp.drone.criadoEm">Criado Em</Translate>
            </span>
          </dt>
          <dd>{droneEntity.criadoEm ? <TextFormat value={droneEntity.criadoEm} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="atualizadoEm">
              <Translate contentKey="dronedeliveryApp.drone.atualizadoEm">Atualizado Em</Translate>
            </span>
          </dt>
          <dd>{droneEntity.atualizadoEm ? <TextFormat value={droneEntity.atualizadoEm} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/drone" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/drone/${droneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DroneDetail;
