export class Jodel {
  id: number;
  parentJodelId: number;
  postId: number;
  childJodelIds: number[];
  content: string;
  upvotes: number;
  downvotes: number;
  jodelIdList: number[];

  constructor(
    id: number,
    parentJodelId: number,
    postId: number,
    childJodelIds: number[],
    content: string,
    upvotes: number,
    downvotes: number,
    jodelIdList: number[]
  ) {
    this.id = id;
    this.parentJodelId = parentJodelId;
    this.postId = postId;
    this.childJodelIds = childJodelIds;
    this.content = content;
    this.upvotes = upvotes;
    this.downvotes = downvotes;
    this.jodelIdList = jodelIdList;
  }
}
