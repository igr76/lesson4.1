package org.example;

import org.example.component.RecordMapper;
import org.example.controller.FacultyController;
import org.example.model.Faculty;
import org.example.record.FacultyRecord;
import org.example.repository.FacultyRepository;
import org.example.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FacultyController.class)
@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private RecordMapper recordMapper;

    @Test
    public void createTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setColor("Красный");
        faculty.setName("Грифиндор");
         when(facultyRepository.save(any())).thenReturn(faculty);

        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setColor("Красный");
        facultyRecord.setName("Грифиндор");

        mockMvc.perform(MockMvcRequestBuilders.post("/faculties")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    FacultyRecord facultyRecordResult = facultyRecord;
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    assertThat(facultyRecordResult).isNotNull();
                    assertThat(facultyRecordResult).usingRecursiveComparison().ignoringFields("id").isEqualTo(facultyRecord);
                    assertThat(facultyRecordResult.getId()).isEqualTo(faculty.getId());
                });
    }
}
