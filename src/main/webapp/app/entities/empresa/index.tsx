import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Empresa from './empresa';
import EmpresaDetail from './empresa-detail';
import EmpresaUpdate from './empresa-update';
import EmpresaDeleteDialog from './empresa-delete-dialog';

const EmpresaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Empresa />} />
    <Route path="new" element={<EmpresaUpdate />} />
    <Route path=":id">
      <Route index element={<EmpresaDetail />} />
      <Route path="edit" element={<EmpresaUpdate />} />
      <Route path="delete" element={<EmpresaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmpresaRoutes;
