import { Barbarian } from "./Barbarian";

export interface Post {
  id: number;
  content: string;
  location: { longitude: number; latitude: number };
  barbarian: Barbarian;
  upvotes: number;
  downvotes: number;
  jodelIdList: number[];
}
