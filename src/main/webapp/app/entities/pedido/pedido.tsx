import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPedido } from 'app/shared/model/pedido.model';
import { getEntities } from './pedido.reducer';

export const Pedido = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const pedidoList = useAppSelector(state => state.pedido.entities);
  const loading = useAppSelector(state => state.pedido.loading);
  const totalItems = useAppSelector(state => state.pedido.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="pedido-heading" data-cy="PedidoHeading">
        <Translate contentKey="dronedeliveryApp.pedido.home.title">Pedidos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dronedeliveryApp.pedido.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pedido/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dronedeliveryApp.pedido.home.createLabel">Create new Pedido</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pedidoList && pedidoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="dronedeliveryApp.pedido.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('codigo')}>
                  <Translate contentKey="dronedeliveryApp.pedido.codigo">Codigo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notaFiscal')}>
                  <Translate contentKey="dronedeliveryApp.pedido.notaFiscal">Nota Fiscal</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('statusPedido')}>
                  <Translate contentKey="dronedeliveryApp.pedido.statusPedido">Status Pedido</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('criadoEm')}>
                  <Translate contentKey="dronedeliveryApp.pedido.criadoEm">Criado Em</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('atualizadoEm')}>
                  <Translate contentKey="dronedeliveryApp.pedido.atualizadoEm">Atualizado Em</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dronedeliveryApp.pedido.cliente">Cliente</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dronedeliveryApp.pedido.empresa">Empresa</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dronedeliveryApp.pedido.endereco">Endereco</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pedidoList.map((pedido, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pedido/${pedido.id}`} color="link" size="sm">
                      {pedido.id}
                    </Button>
                  </td>
                  <td>{pedido.codigo}</td>
                  <td>{pedido.notaFiscal}</td>
                  <td>
                    <Translate contentKey={`dronedeliveryApp.StatusPedido.${pedido.statusPedido}`} />
                  </td>
                  <td>{pedido.criadoEm ? <TextFormat type="date" value={pedido.criadoEm} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pedido.atualizadoEm ? <TextFormat type="date" value={pedido.atualizadoEm} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pedido.cliente ? <Link to={`/cliente/${pedido.cliente.id}`}>{pedido.cliente.id}</Link> : ''}</td>
                  <td>{pedido.empresa ? <Link to={`/empresa/${pedido.empresa.id}`}>{pedido.empresa.id}</Link> : ''}</td>
                  <td>{pedido.endereco ? <Link to={`/endereco/${pedido.endereco.id}`}>{pedido.endereco.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pedido/${pedido.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pedido/${pedido.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pedido/${pedido.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="dronedeliveryApp.pedido.home.notFound">No Pedidos found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={pedidoList && pedidoList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Pedido;
