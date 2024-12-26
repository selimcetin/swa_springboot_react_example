import React, { createContext, useReducer, useContext, ReactNode } from "react";
import { Post } from "../types/Post";

interface State {
  posts: Post[];
}

type Action =
  | { type: "ADD_POST"; payload: Post }
  | { type: "ADD_MULTIPLE_POSTS"; payload: Post[] }
  | { type: "DELETE_POST"; payload: number };

const initialState: State = {
  posts: [],
};

const postReducer = (state: State, action: Action): State => {
  switch (action.type) {
    case "ADD_POST":
      console.log(state.posts);
      return { ...state, posts: [...state.posts, action.payload] };
    case "ADD_MULTIPLE_POSTS":
      return { ...state, posts: [...state.posts, ...action.payload] };
    case "DELETE_POST":
      return {
        ...state,
        posts: state.posts.filter((post) => post.id !== action.payload),
      };
    default:
      return state;
  }
};

interface PostContextType {
  posts: Post[];
  dispatch: React.Dispatch<Action>;
}

const PostContext = createContext<PostContextType | undefined>(undefined);

interface PostProviderProps {
  children: ReactNode;
}

export const PostProvider = ({ children }: PostProviderProps) => {
  const [state, dispatch] = useReducer(postReducer, initialState);

  return (
    <PostContext.Provider value={{ posts: state.posts, dispatch }}>
      {children}
    </PostContext.Provider>
  );
};

export const usePosts = (): PostContextType => {
  const context = useContext(PostContext);
  if (!context) {
    throw new Error("usePosts must be used within a PostProvider");
  }
  return context;
};
