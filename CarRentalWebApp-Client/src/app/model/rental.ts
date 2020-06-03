import {User} from './user';
import {Vehicle} from './vehicle';

export interface Rental {
  id: number;
  user: User,
  vehicle: Vehicle,
  startDate: string,
  endDate: string,
  deleted: boolean
}
