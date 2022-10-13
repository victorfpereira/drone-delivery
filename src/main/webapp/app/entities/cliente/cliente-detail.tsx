import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cliente.reducer';

export const ClienteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clienteEntity = useAppSelector(state => state.cliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clienteDetailsHeading">
          <Translate contentKey="dronedeliveryApp.cliente.detail.title">Cliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="dronedeliveryApp.cliente.id">Id</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="dronedeliveryApp.cliente.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.nome}</dd>
          <dt>
            <span id="documento">
              <Translate contentKey="dronedeliveryApp.cliente.documento">Documento</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.documento}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="dronedeliveryApp.cliente.email">Email</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.email}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="dronedeliveryApp.cliente.status">Status</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="criadoEm">
              <Translate contentKey="dronedeliveryApp.cliente.criadoEm">Criado Em</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.criadoEm ? <TextFormat value={clienteEntity.criadoEm} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="atualizadoEm">
              <Translate contentKey="dronedeliveryApp.cliente.atualizadoEm">Atualizado Em</Translate>
            </span>
          </dt>
          <dd>
            {clienteEntity.atualizadoEm ? <TextFormat value={clienteEntity.atualizadoEm} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cliente/${clienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClienteDetail;
