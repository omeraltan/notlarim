import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonelTanim from './personel-tanim';
import Rehber from './rehber';
import NotBaslikTanim from './not-baslik-tanim';
import Notlar from './notlar';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}personel-tanim`} component={PersonelTanim} />
      <ErrorBoundaryRoute path={`${match.url}rehber`} component={Rehber} />
      <ErrorBoundaryRoute path={`${match.url}not-baslik-tanim`} component={NotBaslikTanim} />
      <ErrorBoundaryRoute path={`${match.url}notlar`} component={Notlar} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
