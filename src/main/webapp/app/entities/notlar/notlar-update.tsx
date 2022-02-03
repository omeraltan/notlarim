import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { INotBaslikTanim } from 'app/shared/model/not-baslik-tanim.model';
import { getEntities as getNotBaslikTanims } from 'app/entities/not-baslik-tanim/not-baslik-tanim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './notlar.reducer';
import { INotlar } from 'app/shared/model/notlar.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NotlarUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const notBaslikTanims = useAppSelector(state => state.notBaslikTanim.entities);
  const notlarEntity = useAppSelector(state => state.notlar.entity);
  const loading = useAppSelector(state => state.notlar.loading);
  const updating = useAppSelector(state => state.notlar.updating);
  const updateSuccess = useAppSelector(state => state.notlar.updateSuccess);
  const handleClose = () => {
    props.history.push('/notlar' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getNotBaslikTanims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...notlarEntity,
      ...values,
      baslik: notBaslikTanims.find(it => it.id.toString() === values.baslik.toString()),
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
          ...notlarEntity,
          baslik: notlarEntity?.baslik?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="notlarimApp.notlar.home.createOrEditLabel" data-cy="NotlarCreateUpdateHeading">
            <Translate contentKey="notlarimApp.notlar.home.createOrEditLabel">Create or edit a Notlar</Translate>
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
                  id="notlar-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('notlarimApp.notlar.not')}
                id="notlar-not"
                name="not"
                data-cy="not"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="notlar-baslik"
                name="baslik"
                data-cy="baslik"
                label={translate('notlarimApp.notlar.baslik')}
                type="select"
              >
                <option value="" key="0" />
                {notBaslikTanims
                  ? notBaslikTanims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notlar" replace color="info">
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

export default NotlarUpdate;
