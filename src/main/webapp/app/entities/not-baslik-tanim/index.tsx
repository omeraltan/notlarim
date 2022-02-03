import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NotBaslikTanim from './not-baslik-tanim';
import NotBaslikTanimDetail from './not-baslik-tanim-detail';
import NotBaslikTanimUpdate from './not-baslik-tanim-update';
import NotBaslikTanimDeleteDialog from './not-baslik-tanim-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NotBaslikTanimUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NotBaslikTanimUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotBaslikTanimDetail} />
      <ErrorBoundaryRoute path={match.url} component={NotBaslikTanim} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NotBaslikTanimDeleteDialog} />
  </>
);

export default Routes;
