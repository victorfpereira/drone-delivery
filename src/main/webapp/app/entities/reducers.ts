import empresa from 'app/entities/empresa/empresa.reducer';
import cliente from 'app/entities/cliente/cliente.reducer';
import drone from 'app/entities/drone/drone.reducer';
import endereco from 'app/entities/endereco/endereco.reducer';
import telefone from 'app/entities/telefone/telefone.reducer';
import pedido from 'app/entities/pedido/pedido.reducer';
import agendamento from 'app/entities/agendamento/agendamento.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  empresa,
  cliente,
  drone,
  endereco,
  telefone,
  pedido,
  agendamento,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
