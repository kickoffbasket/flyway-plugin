package tech.saif;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.google.common.base.CaseFormat;

/**
 * 
 * @author rajatagarwal
 *
 */
@Mojo(name = "create", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
    @Parameter(property = "name", required = true)
    private String name;

    public void execute() throws MojoExecutionException {
        File outputDirectory = new File("src/main/resources/db/migration");
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = "V" + format.format(new Date())
                + "__"
                + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name)
                + ".sql";
        File f = outputDirectory;
        if (!f.exists()) {
            f.mkdirs();
        }
        File migrationFile = new File(f, fileName);
        try (FileWriter w = new FileWriter(migrationFile)) {
            w.write("/* WRITE YOUR SQL */\n");
        }
        catch (IOException e) {
            throw new MojoExecutionException("Error creating file " + migrationFile, e);
        }
    }

}
