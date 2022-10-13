import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pedido.reducer';

export const PedidoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pedidoEntity = useAppSelector(state => state.pedido.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pedidoDetailsHeading">
          <Translate contentKey="dronedeliveryApp.pedido.detail.title">Pedido</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="dronedeliveryApp.pedido.id">Id</Translate>
            </span>
          </dt>
          <dd>{pedidoEntity.id}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="dronedeliveryApp.pedido.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{pedidoEntity.codigo}</dd>
          <dt>
            <span id="notaFiscal">
              <Translate contentKey="dronedeliveryApp.pedido.notaFiscal">Nota Fiscal</Translate>
            </span>
          </dt>
          <dd>{pedidoEntity.notaFiscal}</dd>
          <dt>
            <span id="statusPedido">
              <Translate contentKey="dronedeliveryApp.pedido.statusPedido">Status Pedido</Translate>
            </span>
          </dt>
          <dd>{pedidoEntity.statusPedido}</dd>
          <dt>
            <span id="criadoEm">
              <Translate contentKey="dronedeliveryApp.pedido.criadoEm">Criado Em</Translate>
            </span>
          </dt>
          <dd>{pedidoEntity.criadoEm ? <TextFormat value={pedidoEntity.criadoEm} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="atualizadoEm">
              <Translate contentKey="dronedeliveryApp.pedido.atualizadoEm">Atualizado Em</Translate>
            </span>
          </dt>
          <dd>
            {pedidoEntity.atualizadoEm ? <TextFormat value={pedidoEntity.atualizadoEm} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.pedido.cliente">Cliente</Translate>
          </dt>
          <dd>{pedidoEntity.cliente ? pedidoEntity.cliente.id : ''}</dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.pedido.empresa">Empresa</Translate>
          </dt>
          <dd>{pedidoEntity.empresa ? pedidoEntity.empresa.id : ''}</dd>
          <dt>
            <Translate contentKey="dronedeliveryApp.pedido.endereco">Endereco</Translate>
          </dt>
          <dd>{pedidoEntity.endereco ? pedidoEntity.endereco.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pedido" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pedido/${pedidoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PedidoDetail;
