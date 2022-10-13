import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/empresa">
        <Translate contentKey="global.menu.entities.empresa" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cliente">
        <Translate contentKey="global.menu.entities.cliente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/drone">
        <Translate contentKey="global.menu.entities.drone" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/endereco">
        <Translate contentKey="global.menu.entities.endereco" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/telefone">
        <Translate contentKey="global.menu.entities.telefone" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pedido">
        <Translate contentKey="global.menu.entities.pedido" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/agendamento">
        <Translate contentKey="global.menu.entities.agendamento" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
