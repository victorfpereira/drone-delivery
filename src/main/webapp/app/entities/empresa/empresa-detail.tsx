import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './empresa.reducer';

export const EmpresaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const empresaEntity = useAppSelector(state => state.empresa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="empresaDetailsHeading">
          <Translate contentKey="dronedeliveryApp.empresa.detail.title">Empresa</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="dronedeliveryApp.empresa.id">Id</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.id}</dd>
          <dt>
            <span id="razaoSocial">
              <Translate contentKey="dronedeliveryApp.empresa.razaoSocial">Razao Social</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.razaoSocial}</dd>
          <dt>
            <span id="nomeFantasia">
              <Translate contentKey="dronedeliveryApp.empresa.nomeFantasia">Nome Fantasia</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.nomeFantasia}</dd>
          <dt>
            <span id="inscricaoEstadual">
              <Translate contentKey="dronedeliveryApp.empresa.inscricaoEstadual">Inscricao Estadual</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.inscricaoEstadual}</dd>
          <dt>
            <span id="documento">
              <Translate contentKey="dronedeliveryApp.empresa.documento">Documento</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.documento}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="dronedeliveryApp.empresa.email">Email</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.email}</dd>
          <dt>
            <span id="tipoEmpresa">
              <Translate contentKey="dronedeliveryApp.empresa.tipoEmpresa">Tipo Empresa</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.tipoEmpresa}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="dronedeliveryApp.empresa.status">Status</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="criadoEm">
              <Translate contentKey="dronedeliveryApp.empresa.criadoEm">Criado Em</Translate>
            </span>
          </dt>
          <dd>{empresaEntity.criadoEm ? <TextFormat value={empresaEntity.criadoEm} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="atualizadoEm">
              <Translate contentKey="dronedeliveryApp.empresa.atualizadoEm">Atualizado Em</Translate>
            </span>
          </dt>
          <dd>
            {empresaEntity.atualizadoEm ? <TextFormat value={empresaEntity.atualizadoEm} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/empresa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/empresa/${empresaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmpresaDetail;
