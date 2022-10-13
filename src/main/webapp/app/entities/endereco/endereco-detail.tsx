import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './endereco.reducer';

export const EnderecoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const enderecoEntity = useAppSelector(state => state.endereco.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enderecoDetailsHeading">
          <Translate contentKey="dronedeliveryApp.endereco.detail.title">Endereco</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="dronedeliveryApp.endereco.id">Id</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.id}</dd>
          <dt>
            <span id="rua">
              <Translate contentKey="dronedeliveryApp.endereco.rua">Rua</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.rua}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="dronedeliveryApp.endereco.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.numero}</dd>
          <dt>
            <span id="bairro">
              <Translate contentKey="dronedeliveryApp.endereco.bairro">Bairro</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.bairro}</dd>
          <dt>
            <span id="cidade">
              <Translate contentKey="dronedeliveryApp.endereco.cidade">Cidade</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.cidade}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="dronedeliveryApp.endereco.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.estado}</dd>
          <dt>
            <span id="cep">
              <Translate contentKey="dronedeliveryApp.endereco.cep">Cep</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.cep}</dd>
          <dt>
            <span id="complemento">
              <Translate contentKey="dronedeliveryApp.endereco.complemento">Complemento</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.complemento}</dd>
          <dt>
            <span id="referencia">
              <Translate contentKey="dronedeliveryApp.endereco.referencia">Referencia</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.referencia}</dd>
          <dt>
            <span id="tipoEndereco">
              <Translate contentKey="dronedeliveryApp.endereco.tipoEndereco">Tipo Endereco</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.tipoEndereco}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="dronedeliveryApp.endereco.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="dronedeliveryApp.endereco.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.longitude}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="dronedeliveryApp.endereco.status">Status</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="criadoEm">
              <Translate contentKey="dronedeliveryApp.endereco.criadoEm">Criado Em</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.criadoEm ? <TextFormat value={enderecoEntity.criadoEm} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="atualizadoEm">
              <Translate contentKey="dronedeliveryApp.endereco.atualizadoEm">Atualizado Em</Translate>
            </span>
          </dt>
          <dd>
            {enderecoEntity.atualizadoEm ? <TextFormat value={enderecoEntity.atualizadoEm} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.endereco.empresa">Empresa</Translate>
          </dt>
          <dd>{enderecoEntity.empresa ? enderecoEntity.empresa.id : ''}</dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.endereco.cliente">Cliente</Translate>
          </dt>
          <dd>{enderecoEntity.cliente ? enderecoEntity.cliente.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/endereco" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/endereco/${enderecoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnderecoDetail;
