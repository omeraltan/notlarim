import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonelTanim from './personel-tanim';
import PersonelTanimDetail from './personel-tanim-detail';
import PersonelTanimUpdate from './personel-tanim-update';
import PersonelTanimDeleteDialog from './personel-tanim-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PersonelTanimUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PersonelTanimUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PersonelTanimDetail} />
      <ErrorBoundaryRoute path={match.url} component={PersonelTanim} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PersonelTanimDeleteDialog} />
  </>
);

export default Routes;
