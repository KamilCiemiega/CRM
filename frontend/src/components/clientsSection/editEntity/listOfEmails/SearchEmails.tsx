import { TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import useSendEntity from "../../hooks/useSendEntity";
import { RootState } from "../../../store";
import { ExpandedClient } from "../../topPanel/listOfClientsCompany/helperfunctions/initializeData";

export type FiltredMessage = {
    id: number;
    subject: string;
    time: string;
    size: string;
}

interface SearchMessagesTableProps {
    onSearch: (filtredMessages: FiltredMessage[]) => void;
}

const SearchEmails: React.FC<SearchMessagesTableProps> = ({onSearch}) => {
    const [searchText, setSearchText] = useState("");
    const clickedClient = useSelector((state: RootState) => state.clientView.clickedEntity);
    const clientsDate = useSelector((state: RootState) => state.clientView.companyClientsData);
    const { sendData, clientMessages } = useSendEntity();
    
    const getClientsIds = () => {
        return clientsDate.map(client => client.id);
    }

    const isExpandedClient = (data: any): data is ExpandedClient => {
        return data && "surname" in data;
    };
    

    const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchText(event.target.value);
    };

    const formatDate = (timestamp: number) => {
        const date = new Date(timestamp);
        return date.toLocaleString("en-US", {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit",
        });
    };

    const filterMessages = (query: string) => {
      return clientMessages.filter(message => 
            message.subject.toLowerCase().includes(query.toLowerCase()) ||
            formatDate(Number(message.sentDate)).includes(query) ||
            message.size.toString().includes(query)
        ).map(message => ({
            id: message.id,
            subject: message.subject,
            time: formatDate(Number(message.sentDate)),
            size: message.size.toString()
        }))
    };

    useEffect(() => {
        if(clientMessages.length > 0){
            const filtredMessages = filterMessages(searchText);
            onSearch(filtredMessages)
        }
        

    }, [searchText, clientMessages])


    
    useEffect(() => {
        let clientsIds;
        const isClient = isExpandedClient(clickedClient);
        isClient? clientsIds = [clickedClient.id] : clientsIds = getClientsIds();

        
        const endpoint = "http://localdev:8082/api/messages/clients/messages";
        sendData({url: endpoint, value: clientsIds, getData: true})

    }, [clientsDate, clickedClient])

    return (
        <TextField
        fullWidth
        label="Search" 
        variant="outlined" 
        value={searchText} 
        onChange={handleSearch} 
        margin="normal" 
    />
    );

}

export default SearchEmails;