import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './notlar.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NotlarDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const notlarEntity = useAppSelector(state => state.notlar.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notlarDetailsHeading">
          <Translate contentKey="notlarimApp.notlar.detail.title">Notlar</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notlarEntity.id}</dd>
          <dt>
            <span id="not">
              <Translate contentKey="notlarimApp.notlar.not">Not</Translate>
            </span>
          </dt>
          <dd>{notlarEntity.not}</dd>
          <dt>
            <Translate contentKey="notlarimApp.notlar.baslik">Baslik</Translate>
          </dt>
          <dd>{notlarEntity.baslik ? notlarEntity.baslik.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notlar" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notlar/${notlarEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotlarDetail;
