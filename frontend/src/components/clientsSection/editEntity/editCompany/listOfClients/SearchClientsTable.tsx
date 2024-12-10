import { TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../../store";
import { Client } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { clientViewAction } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

interface SearchClientsTableProps {
    onSearch: (filteredClients: Client[]) => void;
}


const SearchClientsTable: React.FC<SearchClientsTableProps> = ({onSearch}) => {
    const dispatch = useDispatch();
    const [searchText, setSearchText] = useState("");
    const clickedEntityData = useSelector((state: RootState) => state.clientView.clickedEntity);

    const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchText(event.target.value);
    }; 
    
    const hasClients = (data: any): data is { clients: Client[] } => {
        return data && Array.isArray(data.clients);
    };

    const filterClients = (clients: Client[], query: string) => {
        const lowerCaseQuery = query.toLowerCase();
        return clients.filter(client => 
            client.name.toLowerCase().includes(lowerCaseQuery) ||
            client.surname.toLowerCase().includes(lowerCaseQuery) ||
            client.email.toLowerCase().includes(lowerCaseQuery)
        );
    };
    
    useEffect(() => {
        if (hasClients(clickedEntityData)) {
            dispatch(clientViewAction.setCompanyClientsData(clickedEntityData.clients));
            const filteredClients = filterClients(clickedEntityData.clients, searchText);
            onSearch(filteredClients);
        }
    }, [searchText, clickedEntityData]);

    return (
        <TextField
            sx={{width: '90%', mr: '5%'}} 
            label="Search" 
            variant="outlined" 
            value={searchText} 
            onChange={handleSearch} 
            margin="normal" 
        />
    );
}

export default SearchClientsTable;