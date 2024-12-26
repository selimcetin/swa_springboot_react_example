import React, { useState, useEffect } from "react";
import { Jodel } from "../types/Jodel";
import { JodelService } from "../services/JodelService";
import JodelElement from "./JodelElement";

const JodelList: React.FC = ({}) => {
  const [jodels, setJodels] = useState<Jodel[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    (async () => {
      try {
        const fetchedJodels = await JodelService.fetchAllJodels();
        setJodels(fetchedJodels);
      } catch (err) {
        setError("Failed to load posts");
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div>
      {jodels.length > 0 ? (
        jodels.map((jodel) => <JodelElement jodel={jodel}></JodelElement>)
      ) : (
        <p>No Jodels available.</p>
      )}
    </div>
  );
};

export default JodelList;
