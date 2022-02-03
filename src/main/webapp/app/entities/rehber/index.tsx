import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Rehber from './rehber';
import RehberDetail from './rehber-detail';
import RehberUpdate from './rehber-update';
import RehberDeleteDialog from './rehber-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RehberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RehberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RehberDetail} />
      <ErrorBoundaryRoute path={match.url} component={Rehber} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RehberDeleteDialog} />
  </>
);

export default Routes;
