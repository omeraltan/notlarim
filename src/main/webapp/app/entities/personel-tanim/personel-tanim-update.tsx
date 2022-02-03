import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './personel-tanim.reducer';
import { IPersonelTanim } from 'app/shared/model/personel-tanim.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Gorev } from 'app/shared/model/enumerations/gorev.model';

export const PersonelTanimUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const personelTanimEntity = useAppSelector(state => state.personelTanim.entity);
  const loading = useAppSelector(state => state.personelTanim.loading);
  const updating = useAppSelector(state => state.personelTanim.updating);
  const updateSuccess = useAppSelector(state => state.personelTanim.updateSuccess);
  const gorevValues = Object.keys(Gorev);
  const handleClose = () => {
    props.history.push('/personel-tanim' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...personelTanimEntity,
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
      ? {}
      : {
          gorevtip: 'J_DEV_MEM',
          ...personelTanimEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="notlarimApp.personelTanim.home.createOrEditLabel" data-cy="PersonelTanimCreateUpdateHeading">
            <Translate contentKey="notlarimApp.personelTanim.home.createOrEditLabel">Create or edit a PersonelTanim</Translate>
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
                  id="personel-tanim-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('notlarimApp.personelTanim.adi')}
                id="personel-tanim-adi"
                name="adi"
                data-cy="adi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('notlarimApp.personelTanim.soyadi')}
                id="personel-tanim-soyadi"
                name="soyadi"
                data-cy="soyadi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('notlarimApp.personelTanim.gorevtip')}
                id="personel-tanim-gorevtip"
                name="gorevtip"
                data-cy="gorevtip"
                type="select"
              >
                {gorevValues.map(gorev => (
                  <option value={gorev} key={gorev}>
                    {translate('notlarimApp.Gorev.' + gorev)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/personel-tanim" replace color="info">
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

export default PersonelTanimUpdate;
