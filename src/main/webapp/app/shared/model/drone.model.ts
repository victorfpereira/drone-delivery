import dayjs from 'dayjs';
import { IAgendamento } from 'app/shared/model/agendamento.model';
import { StatusDrone } from 'app/shared/model/enumerations/status-drone.model';

export interface IDrone {
  id?: string;
  codigo?: string;
  nome?: string;
  descricao?: string | null;
  statusDrone?: StatusDrone | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  agendamentos?: IAgendamento[] | null;
}

export const defaultValue: Readonly<IDrone> = {};
