import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './telefone.reducer';

export const TelefoneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const telefoneEntity = useAppSelector(state => state.telefone.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telefoneDetailsHeading">
          <Translate contentKey="dronedeliveryApp.telefone.detail.title">Telefone</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="dronedeliveryApp.telefone.id">Id</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.id}</dd>
          <dt>
            <span id="ddd">
              <Translate contentKey="dronedeliveryApp.telefone.ddd">Ddd</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.ddd}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="dronedeliveryApp.telefone.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.numero}</dd>
          <dt>
            <span id="tipoTelefone">
              <Translate contentKey="dronedeliveryApp.telefone.tipoTelefone">Tipo Telefone</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.tipoTelefone}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="dronedeliveryApp.telefone.status">Status</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="criadoEm">
              <Translate contentKey="dronedeliveryApp.telefone.criadoEm">Criado Em</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.criadoEm ? <TextFormat value={telefoneEntity.criadoEm} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="atualizadoEm">
              <Translate contentKey="dronedeliveryApp.telefone.atualizadoEm">Atualizado Em</Translate>
            </span>
          </dt>
          <dd>
            {telefoneEntity.atualizadoEm ? <TextFormat value={telefoneEntity.atualizadoEm} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.telefone.empresa">Empresa</Translate>
          </dt>
          <dd>{telefoneEntity.empresa ? telefoneEntity.empresa.id : ''}</dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.telefone.cliente">Cliente</Translate>
          </dt>
          <dd>{telefoneEntity.cliente ? telefoneEntity.cliente.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/telefone" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telefone/${telefoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TelefoneDetail;
