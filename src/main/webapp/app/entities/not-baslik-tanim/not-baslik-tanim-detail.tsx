import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './not-baslik-tanim.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NotBaslikTanimDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const notBaslikTanimEntity = useAppSelector(state => state.notBaslikTanim.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notBaslikTanimDetailsHeading">
          <Translate contentKey="notlarimApp.notBaslikTanim.detail.title">NotBaslikTanim</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notBaslikTanimEntity.id}</dd>
          <dt>
            <span id="baslik">
              <Translate contentKey="notlarimApp.notBaslikTanim.baslik">Baslik</Translate>
            </span>
          </dt>
          <dd>{notBaslikTanimEntity.baslik}</dd>
        </dl>
        <Button tag={Link} to="/not-baslik-tanim" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/not-baslik-tanim/${notBaslikTanimEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotBaslikTanimDetail;
