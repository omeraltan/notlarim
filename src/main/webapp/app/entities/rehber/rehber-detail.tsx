import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './rehber.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RehberDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const rehberEntity = useAppSelector(state => state.rehber.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rehberDetailsHeading">
          <Translate contentKey="notlarimApp.rehber.detail.title">Rehber</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rehberEntity.id}</dd>
          <dt>
            <span id="telefon">
              <Translate contentKey="notlarimApp.rehber.telefon">Telefon</Translate>
            </span>
          </dt>
          <dd>{rehberEntity.telefon}</dd>
          <dt>
            <span id="adres">
              <Translate contentKey="notlarimApp.rehber.adres">Adres</Translate>
            </span>
          </dt>
          <dd>{rehberEntity.adres}</dd>
          <dt>
            <Translate contentKey="notlarimApp.rehber.personel">Personel</Translate>
          </dt>
          <dd>{rehberEntity.personel ? rehberEntity.personel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rehber" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rehber/${rehberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RehberDetail;
