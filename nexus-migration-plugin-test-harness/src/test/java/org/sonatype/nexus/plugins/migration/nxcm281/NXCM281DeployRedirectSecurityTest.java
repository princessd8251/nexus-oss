package org.sonatype.nexus.plugins.migration.nxcm281;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsecurity.codec.Base64;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.data.Status;
import org.sonatype.nexus.integrationtests.TestContainer;
import org.sonatype.nexus.integrationtests.TestContext;
import org.sonatype.nexus.plugin.migration.artifactory.dto.MigrationSummaryDTO;
import org.sonatype.nexus.plugins.migration.AbstractMigrationIntegrationTest;
import org.sonatype.nexus.test.utils.TaskScheduleUtil;

public class NXCM281DeployRedirectSecurityTest
    extends AbstractMigrationIntegrationTest
{

    @BeforeClass
    public static void start()
    {
        TestContainer.getInstance().getTestContext().setSecureTest( true );
    }

    @Override
    protected void runOnce()
        throws Exception
    {
        MigrationSummaryDTO migrationSummary = prepareMigration( getTestFile( "artifactoryBackup.zip" ) );
        commitMigration( migrationSummary );

        TaskScheduleUtil.waitForTasks( 40 );
        Thread.sleep( 2000 );
    }

    @Test
    public void deployWithPermition()
        throws Exception
    {
        TestContainer.getInstance().getTestContext().useAdminForRequests();

        int returnCode = deploy();
        Assert.assertTrue( "Unable to deploy artifact " + returnCode, Status.isSuccess( returnCode ) );
    }

    @Test
    public void deployWithoutPermition()
        throws Exception
    {
        TestContainer.getInstance().getTestContext().setUsername( "dummy" );

        Assert.assertEquals( "Unable to deploy artifact ", 401, deploy() );
    }

    private int deploy()
        throws IOException
    {
        File artifact = getTestFile( "artifact.jar" );
        String path = "nxcm281/deploy/released/1.0/released-1.0.jar";
        String deployUrl = "http://localhost:" + nexusApplicationPort + "/artifactory/main-local/";
        // wagon is not sending authentication
        // DeployUtils.deployWithWagon( this.container, "http", deployUrl, artifact, path );

        URL url = new URL( deployUrl + path );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        TestContext testContext = TestContainer.getInstance().getTestContext();
        String userPassword = testContext.getUsername() + ":" + testContext.getPassword();
        userPassword = Base64.encodeToString( userPassword.getBytes() );
        userPassword = "Basic " + userPassword;
        conn.setRequestProperty( "Authorization", userPassword );
        conn.setDoOutput( true );
        conn.setRequestMethod( "PUT" );
        byte[] bytes = FileUtils.readFileToByteArray( artifact );
        OutputStream out = conn.getOutputStream();
        IOUtils.write( bytes, out );
        out.close();

        return conn.getResponseCode();
    }
}
