import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './personel-tanim.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonelTanimDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personelTanimEntity = useAppSelector(state => state.personelTanim.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personelTanimDetailsHeading">
          <Translate contentKey="notlarimApp.personelTanim.detail.title">PersonelTanim</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personelTanimEntity.id}</dd>
          <dt>
            <span id="adi">
              <Translate contentKey="notlarimApp.personelTanim.adi">Adi</Translate>
            </span>
          </dt>
          <dd>{personelTanimEntity.adi}</dd>
          <dt>
            <span id="soyadi">
              <Translate contentKey="notlarimApp.personelTanim.soyadi">Soyadi</Translate>
            </span>
          </dt>
          <dd>{personelTanimEntity.soyadi}</dd>
          <dt>
            <span id="gorevtip">
              <Translate contentKey="notlarimApp.personelTanim.gorevtip">Gorevtip</Translate>
            </span>
          </dt>
          <dd>{personelTanimEntity.gorevtip}</dd>
        </dl>
        <Button tag={Link} to="/personel-tanim" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/personel-tanim/${personelTanimEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonelTanimDetail;
