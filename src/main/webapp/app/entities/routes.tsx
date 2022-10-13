import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Empresa from './empresa';
import Cliente from './cliente';
import Drone from './drone';
import Endereco from './endereco';
import Telefone from './telefone';
import Pedido from './pedido';
import Agendamento from './agendamento';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="empresa/*" element={<Empresa />} />
        <Route path="cliente/*" element={<Cliente />} />
        <Route path="drone/*" element={<Drone />} />
        <Route path="endereco/*" element={<Endereco />} />
        <Route path="telefone/*" element={<Telefone />} />
        <Route path="pedido/*" element={<Pedido />} />
        <Route path="agendamento/*" element={<Agendamento />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
