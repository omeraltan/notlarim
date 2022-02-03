import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Notlar from './notlar';
import NotlarDetail from './notlar-detail';
import NotlarUpdate from './notlar-update';
import NotlarDeleteDialog from './notlar-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NotlarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NotlarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotlarDetail} />
      <ErrorBoundaryRoute path={match.url} component={Notlar} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NotlarDeleteDialog} />
  </>
);

export default Routes;
