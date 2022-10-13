import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Drone from './drone';
import DroneDetail from './drone-detail';
import DroneUpdate from './drone-update';
import DroneDeleteDialog from './drone-delete-dialog';

const DroneRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Drone />} />
    <Route path="new" element={<DroneUpdate />} />
    <Route path=":id">
      <Route index element={<DroneDetail />} />
      <Route path="edit" element={<DroneUpdate />} />
      <Route path="delete" element={<DroneDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DroneRoutes;
