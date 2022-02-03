import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPersonelTanim } from 'app/shared/model/personel-tanim.model';
import { getEntities as getPersonelTanims } from 'app/entities/personel-tanim/personel-tanim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rehber.reducer';
import { IRehber } from 'app/shared/model/rehber.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RehberUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const personelTanims = useAppSelector(state => state.personelTanim.entities);
  const rehberEntity = useAppSelector(state => state.rehber.entity);
  const loading = useAppSelector(state => state.rehber.loading);
  const updating = useAppSelector(state => state.rehber.updating);
  const updateSuccess = useAppSelector(state => state.rehber.updateSuccess);
  const handleClose = () => {
    props.history.push('/rehber' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPersonelTanims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...rehberEntity,
      ...values,
      personel: personelTanims.find(it => it.id.toString() === values.personel.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...rehberEntity,
          personel: rehberEntity?.personel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="notlarimApp.rehber.home.createOrEditLabel" data-cy="RehberCreateUpdateHeading">
            <Translate contentKey="notlarimApp.rehber.home.createOrEditLabel">Create or edit a Rehber</Translate>
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
                  id="rehber-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('notlarimApp.rehber.telefon')}
                id="rehber-telefon"
                name="telefon"
                data-cy="telefon"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('notlarimApp.rehber.adres')} id="rehber-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField
                id="rehber-personel"
                name="personel"
                data-cy="personel"
                label={translate('notlarimApp.rehber.personel')}
                type="select"
              >
                <option value="" key="0" />
                {personelTanims
                  ? personelTanims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rehber" replace color="info">
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

export default RehberUpdate;
