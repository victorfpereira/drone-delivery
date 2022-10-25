import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { TipoEmpresa } from 'app/shared/model/enumerations/tipo-empresa.model';
import { getEntity, updateEntity, createEntity, reset } from './empresa.reducer';

export const EmpresaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const empresaEntity = useAppSelector(state => state.empresa.entity);
  const loading = useAppSelector(state => state.empresa.loading);
  const updating = useAppSelector(state => state.empresa.updating);
  const updateSuccess = useAppSelector(state => state.empresa.updateSuccess);
  const tipoEmpresaValues = Object.keys(TipoEmpresa);

  const handleClose = () => {
    navigate('/empresa' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.criadoEm = convertDateTimeToServer(values.criadoEm);
    values.atualizadoEm = convertDateTimeToServer(values.atualizadoEm);

    const entity = {
      ...empresaEntity,
      ...values,
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
          criadoEm: displayDefaultDateTime(),
          atualizadoEm: displayDefaultDateTime(),
        }
      : {
          tipoEmpresa: 'COMERCIO',
          ...empresaEntity,
          criadoEm: convertDateTimeFromServer(empresaEntity.criadoEm),
          atualizadoEm: convertDateTimeFromServer(empresaEntity.atualizadoEm),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dronedeliveryApp.empresa.home.createOrEditLabel" data-cy="EmpresaCreateUpdateHeading">
            <Translate contentKey="dronedeliveryApp.empresa.home.createOrEditLabel">Create or edit a Empresa</Translate>
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
                  id="empresa-id"
                  label={translate('dronedeliveryApp.empresa.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.razaoSocial')}
                id="empresa-razaoSocial"
                name="razaoSocial"
                data-cy="razaoSocial"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.nomeFantasia')}
                id="empresa-nomeFantasia"
                name="nomeFantasia"
                data-cy="nomeFantasia"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.inscricaoEstadual')}
                id="empresa-inscricaoEstadual"
                name="inscricaoEstadual"
                data-cy="inscricaoEstadual"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.documento')}
                id="empresa-documento"
                name="documento"
                data-cy="documento"
                type="text"
                validate={{
                  min: { value: 14, message: translate('entity.validation.min', { min: 14 }) },
                  max: { value: 14, message: translate('entity.validation.max', { max: 14 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.email')}
                id="empresa-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.tipoEmpresa')}
                id="empresa-tipoEmpresa"
                name="tipoEmpresa"
                data-cy="tipoEmpresa"
                type="select"
              >
                {tipoEmpresaValues.map(tipoEmpresa => (
                  <option value={tipoEmpresa} key={tipoEmpresa}>
                    {translate('dronedeliveryApp.TipoEmpresa.' + tipoEmpresa)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.status')}
                id="empresa-status"
                name="status"
                data-cy="status"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.criadoEm')}
                id="empresa-criadoEm"
                name="criadoEm"
                data-cy="criadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dronedeliveryApp.empresa.atualizadoEm')}
                id="empresa-atualizadoEm"
                name="atualizadoEm"
                data-cy="atualizadoEm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/empresa" replace color="info">
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

export default EmpresaUpdate;
