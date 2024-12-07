export class Post {
  id: number;
  content: string;
  location: { longitude: number; latitude: number };
  barbarianUsername: string;
  upvotes: number =0;
  downvotes: number;
  jodelIdList: number[] = [];

  constructor(
    id: number,
    content: string,
    location: { longitude: number; latitude: number },
    barbarianUsername: string,
    upvotes: number,
    downvotes: number,
    jodelIdList: number[] = []
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
