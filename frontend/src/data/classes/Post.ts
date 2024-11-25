import { Location } from "../../interfaces/Location";

export class Post {
  id: number;
  content: string;
  location: Location; // Use the imported Location interface
  barbarianUsername: string;
  upvotes: number;
  downvotes: number;
  jodelIdList: number[];

  constructor(
    id: number,
    content: string,
    location: Location,
    barbarianUsername: string,
    upvotes: number,
    downvotes: number,
    jodelIdList: number[]
  ) {
    this.id = id;
    this.content = content;
    this.location = location;
    this.barbarianUsername = barbarianUsername;
    this.upvotes = upvotes;
    this.downvotes = downvotes;
    this.jodelIdList = jodelIdList;
  }
}
