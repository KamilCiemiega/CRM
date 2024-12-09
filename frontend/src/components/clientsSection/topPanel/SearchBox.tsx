import { IconButton, InputBase, Paper } from "@mui/material";
import { Search } from "@mui/icons-material";
import { useState } from "react";
import { AppDispatch } from "../../store";
import { useDispatch } from "react-redux";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";


const SearchBox = () => {
    const dispatch = useDispatch<AppDispatch>();
    const [searchValue, setSearchValue] = useState("");

    const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchValue(event.target.value);
        dispatch(clientViewAction.setSearchValue(event.target.value));
    };
 
    return (
        <Paper
            component="form"
            sx={{ p: "2px 4px", display: "flex", alignItems: "center", width: 400, height: 50 }}
        >
            <InputBase
                sx={{ ml: 1, flex: 1 }}
                placeholder="Search"
                inputProps={{ "aria-label": "search" }}
                value={searchValue}
                onChange={handleSearch}
            />
            <IconButton type="button" sx={{ p: "10px" }} aria-label="search">
                <Search />
            </IconButton>
        </Paper>
    );
};

export default SearchBox;