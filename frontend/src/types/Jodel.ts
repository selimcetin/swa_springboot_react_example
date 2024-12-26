import { Barbarian } from "./Barbarian";

export interface Jodel {
  id?: number;
  parentJodelId?: number;
  postId: number;
  barbarian: Barbarian;
  childJodelIds?: number[];
  content: string;
  upvotes?: number;
  downvotes?: number;
  jodelIdList?: number[];
}
