import { useState, useEffect } from "react";
import { TextField } from "@mui/material";
import { Rows } from "../../../../interfaces/interfaces";

interface SearchTableDataProps {
    rows: Rows[];
    onSearch: (filteredRows: Rows[]) => void;
}

const SearchTableData: React.FC<SearchTableDataProps> = ({ rows, onSearch }) => {
    const [searchText, setSearchText] = useState("");

    const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchText(event.target.value);
    };

    useEffect(() => {
        const filtered = rows.filter(row => 
            row.subject.toLowerCase().includes(searchText.toLowerCase()) ||
            row.sendDate.includes(searchText) ||
            row.size.toString().includes(searchText)
        );
        onSearch(filtered);
    }, [searchText, rows, onSearch]);

    return (
        <TextField 
            label="Search" 
            variant="outlined" 
            value={searchText} 
            onChange={handleSearch} 
            fullWidth 
            margin="normal" 
        />
    );
}

export default SearchTableData;
