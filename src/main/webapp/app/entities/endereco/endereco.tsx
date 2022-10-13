import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEndereco } from 'app/shared/model/endereco.model';
import { getEntities } from './endereco.reducer';

export const Endereco = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const enderecoList = useAppSelector(state => state.endereco.entities);
  const loading = useAppSelector(state => state.endereco.loading);
  const totalItems = useAppSelector(state => state.endereco.totalItems);

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
      <h2 id="endereco-heading" data-cy="EnderecoHeading">
        <Translate contentKey="dronedeliveryApp.endereco.home.title">Enderecos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dronedeliveryApp.endereco.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/endereco/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dronedeliveryApp.endereco.home.createLabel">Create new Endereco</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {enderecoList && enderecoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="dronedeliveryApp.endereco.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rua')}>
                  <Translate contentKey="dronedeliveryApp.endereco.rua">Rua</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numero')}>
                  <Translate contentKey="dronedeliveryApp.endereco.numero">Numero</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('bairro')}>
                  <Translate contentKey="dronedeliveryApp.endereco.bairro">Bairro</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cidade')}>
                  <Translate contentKey="dronedeliveryApp.endereco.cidade">Cidade</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('estado')}>
                  <Translate contentKey="dronedeliveryApp.endereco.estado">Estado</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cep')}>
                  <Translate contentKey="dronedeliveryApp.endereco.cep">Cep</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('complemento')}>
                  <Translate contentKey="dronedeliveryApp.endereco.complemento">Complemento</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('referencia')}>
                  <Translate contentKey="dronedeliveryApp.endereco.referencia">Referencia</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tipoEndereco')}>
                  <Translate contentKey="dronedeliveryApp.endereco.tipoEndereco">Tipo Endereco</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('latitude')}>
                  <Translate contentKey="dronedeliveryApp.endereco.latitude">Latitude</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('longitude')}>
                  <Translate contentKey="dronedeliveryApp.endereco.longitude">Longitude</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="dronedeliveryApp.endereco.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('criadoEm')}>
                  <Translate contentKey="dronedeliveryApp.endereco.criadoEm">Criado Em</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('atualizadoEm')}>
                  <Translate contentKey="dronedeliveryApp.endereco.atualizadoEm">Atualizado Em</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dronedeliveryApp.endereco.empresa">Empresa</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dronedeliveryApp.endereco.cliente">Cliente</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {enderecoList.map((endereco, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/endereco/${endereco.id}`} color="link" size="sm">
                      {endereco.id}
                    </Button>
                  </td>
                  <td>{endereco.rua}</td>
                  <td>{endereco.numero}</td>
                  <td>{endereco.bairro}</td>
                  <td>{endereco.cidade}</td>
                  <td>{endereco.estado}</td>
                  <td>{endereco.cep}</td>
                  <td>{endereco.complemento}</td>
                  <td>{endereco.referencia}</td>
                  <td>
                    <Translate contentKey={`dronedeliveryApp.TipoEndereco.${endereco.tipoEndereco}`} />
                  </td>
                  <td>{endereco.latitude}</td>
                  <td>{endereco.longitude}</td>
                  <td>{endereco.status ? 'true' : 'false'}</td>
                  <td>{endereco.criadoEm ? <TextFormat type="date" value={endereco.criadoEm} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {endereco.atualizadoEm ? <TextFormat type="date" value={endereco.atualizadoEm} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{endereco.empresa ? <Link to={`/empresa/${endereco.empresa.id}`}>{endereco.empresa.id}</Link> : ''}</td>
                  <td>{endereco.cliente ? <Link to={`/cliente/${endereco.cliente.id}`}>{endereco.cliente.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/endereco/${endereco.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/endereco/${endereco.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/endereco/${endereco.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="dronedeliveryApp.endereco.home.notFound">No Enderecos found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={enderecoList && enderecoList.length > 0 ? '' : 'd-none'}>
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

export default Endereco;
