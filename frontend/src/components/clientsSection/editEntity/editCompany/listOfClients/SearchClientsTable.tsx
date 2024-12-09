import { TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../../store";
import { Client } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

interface SearchClientsTableProps {
    onSearch: (filteredClients: Client[]) => void;
}


const SearchClientsTable: React.FC<SearchClientsTableProps> = ({onSearch}) => {
    const [searchText, setSearchText] = useState("");
    const clickedEntityData = useSelector((state: RootState) => state.clientView.clickedEntity);

    const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchText(event.target.value);
    };
    
    useEffect(() => {
        if (clickedEntityData && 'clients' in clickedEntityData && clickedEntityData.clients != null) {
                const filtredClients =clickedEntityData.clients?.filter(client => 
                    client.name.includes(searchText.toLowerCase()) ||
                    client.surname.includes(searchText.toLowerCase()) ||
                    client.email.includes(searchText.toLowerCase())
                )
                console.log(filtredClients)
                onSearch(filtredClients);            
        } 

    }, [searchText])

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