import { useState, useEffect } from "react";
import axios from "axios";

// Typy danych
interface MessageRole {
  status: "TO" | "CC";
  participantId: number;
}

interface ParticipantData {
  status: string;
  participantId: number;
  [key: string]: any; 
}


export const useParticipantsData = (messageRoles: MessageRole[]) => {
  const [participantsData, setParticipantsData] = useState<ParticipantData[]>([]);

  useEffect(() => {
    const fetchParticipants = async () => {
      try {
        const data = await Promise.all(
          messageRoles.map(async (role) => {
            const response = await axios.get(
              `http://localdev:8082/api/message-participant/by-id?participantId=${role.participantId}`
            );
            return { status: role.status, participantId: role.participantId, ...response.data };
          })
        );
        setParticipantsData(data);
      } catch (error) {
        console.error("Error fetching participant data:", error);
      }
    };

    fetchParticipants();
  }, [messageRoles]);

  return participantsData;
};
