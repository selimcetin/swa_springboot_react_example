import { Jodel } from "../types/Jodel";

interface JodelCardProps {
  jodel: Jodel;
}

const JodelElement: React.FC<JodelCardProps> = ({ jodel }) => {
  return (
    <div>
      <div>{jodel.barbarian.username}</div>
      <div>{jodel.content}</div>
    </div>
  );
};

export default JodelElement;
