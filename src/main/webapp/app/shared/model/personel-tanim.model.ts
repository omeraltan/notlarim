import { IRehber } from 'app/shared/model/rehber.model';
import { Gorev } from 'app/shared/model/enumerations/gorev.model';

export interface IPersonelTanim {
  id?: number;
  adi?: string;
  soyadi?: string;
  gorevtip?: Gorev;
  rehbers?: IRehber[] | null;
}

export const defaultValue: Readonly<IPersonelTanim> = {};
