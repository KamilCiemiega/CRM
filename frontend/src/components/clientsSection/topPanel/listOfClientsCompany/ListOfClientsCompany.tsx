import React, { useEffect, useState } from "react";
import { Box, Checkbox, Paper, Typography } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../store";
import "../../../../style/ListOfClientsCompany.css";
import { ThemeProvider } from "@emotion/react";
import ListOfClientsTheme from "../../../../themes/ListOfClientsCompanyTheme";
import { initializeData } from "./helperfunctions/initializeData";
import { ExpandedCompany } from "./helperfunctions/initializeData";
import { ExpandedClient } from "./helperfunctions/initializeData";
import { clientViewAction } from "../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { CheckBox } from "@mui/icons-material";

const ListOfClientsCompany = () => {
    const dispatch = useDispatch<AppDispatch>();
    const [filteredView, setFilteredView] = useState<any[]>([]);
    const [checkedItems, setCheckedItems] = useState<{ [key: number]: boolean }>({});
    const clientData = useSelector((state: RootState) => state.clientView.clientsData);
    const companyData = useSelector((state: RootState) => state.clientView.companyData);
    const typeOfView = useSelector((state: RootState) => state.clientView.viewType);
    const searchValue = useSelector((state: RootState) => state.clientView.searchValue);
    
    useEffect(() => {
        const { filtredView, companiesData } = initializeData({ clientData,companyData, typeOfView, searchValue });

        setFilteredView(filtredView);
        dispatch(clientViewAction.setExpandedCompanyData(companiesData));
    }, [clientData, companyData, typeOfView, searchValue]);

    const isExpandedClient = (entity: ExpandedClient | ExpandedCompany): entity is ExpandedClient => {
        return (entity as ExpandedClient).surname !== undefined;
    };

    const handleEntityClick = (entity: ExpandedClient | ExpandedCompany) => {
        dispatch(clientViewAction.setClickedEntity(entity));
        if(isExpandedClient(entity)){
            dispatch(clientViewAction.setEditEntityViewType("clients"));
        }else {
            dispatch(clientViewAction.setEditEntityViewType("companies"));
        }
    }

    const handleCheckboxChange = (id: number) => {
        setCheckedItems((prev) => ({
            ...prev,
            [id]: !prev[id]
        }));
    };

    useEffect(() => {
        console.log(checkedItems)
    }, [checkedItems, setCheckedItems])

    return (
        <Box
            sx={{
                display: "grid",                 
                gridTemplateColumns: "repeat(3, 1fr)",  
                mt: '15px',                    
                width: "100%",                        
            }}
        >  
                {filteredView.map((entity, index) => (
                    <ThemeProvider theme={ListOfClientsTheme} key={index}>
                        <Paper>
                        <Checkbox
                            checked={checkedItems[entity.id] || false}
                            onChange={() => handleCheckboxChange(entity.id)}
                        />
                            <Box sx={{display: 'flex', alignItems: 'center'}} onClick={() => handleEntityClick(entity)}>
                            <Box
                                className="listOfClientsImage"
                                sx={{
                                    backgroundImage: `url(${entity.image})`,
                                    width: "80px",
                                    height: "80px",
                                    backgroundSize: "cover",
                                    backgroundPosition: "center",
                                    borderRadius: "8px",
                                }}
                            />
                            {typeOfView === 'clients' ?
                                <Typography sx={{ ml: "20px" }}>{`${entity.name} ${entity.surname}`}</Typography> :
                                <Typography sx={{ ml: "20px" }}>{`${entity.name}`}</Typography>
                            }
                            </Box>
                        </Paper>
                    </ThemeProvider>
                ))}
        </Box>
    );
};

export default ListOfClientsCompany;